package com.imaginaryebay.Models;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.model.UnsupportedMediaTypeException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Brian on 6/27/2016.
 *
 * S3FileUploader - Handles file uploads, configures upload settings for S3, and manages URL storage.
 */
public class S3FileUploader {


    /**
     * Define some constants for setting credentials, and building URLs to store in the DB
     */
    private static final String         IMAGE_JPEG      = "image/jpeg";
    private static final String         IMAGE_PNG       = "image/png";
    private static final String         FILENAME_HEADER = "filename";
    private static final String         BUCKET          = "imaginaryebay";
    private static final String         THUMB_SUFFIX    = "_thumb";
    private static final String         INVALID_TYPE    = "Invalid file type. Files must be either .png or .jpg format.";
    private static final AWSCredentials AWS_CREDENTIALS = new BasicAWSCredentials(System.getenv("AWS_PUBLIC"),
                                                                                  System.getenv("AWS_SECRET"));

    /**
     * // TODO: Could probably make this more space-efficient at least. But for our purposes this should be okay.
     * fileUploader - Uploads a MultipartFile object to S3.
     * @param multipartFile- A MultipartFile to be uploaded to S3. The URL of this file will be stored.
     * @return String result - The URL to access the uploaded MultiPartFile
     * @throws Exception
     */
    public String fileUploader(MultipartFile multipartFile) throws Exception{
        String keyName = "image-" + UUID.randomUUID();
        AmazonS3 s3 = new AmazonS3Client(AWS_CREDENTIALS);
        String result = null;
        try(S3Object s3FullImageObject = new S3Object();
            S3Object s3ThumbnailImageObject = new S3Object()) {

            String fileType = multipartFile.getContentType();
            String fileName = multipartFile.getName();

            // Only accept jpeg and png
            if (!(IMAGE_JPEG.equals(fileType) || IMAGE_PNG.equals(fileType))){
                throw new UnsupportedMediaTypeException(INVALID_TYPE);
            }

            // Get Original and thumbnail bytes
            byte[] originalBytes = multipartFile.getBytes();
            byte[] thumbnailBytes = getThumbnailBytes(originalBytes);

            System.out.println("Here");

            // Create Object Metadata for S3
            ObjectMetadata omdOriginal = getImageObjectMetadata(fileType, fileName, originalBytes.length);
            ObjectMetadata omdThumbnail = getImageObjectMetadata(fileType, fileName + THUMB_SUFFIX, thumbnailBytes.length);

            // Create Input Streams
            InputStream bisOriginal = new ByteArrayInputStream(originalBytes);
            InputStream bisThumbnail = new ByteArrayInputStream(thumbnailBytes);

            /** Upload the Original image to S3 */
            s3FullImageObject.setObjectContent(bisOriginal);
            s3.putObject(new PutObjectRequest(BUCKET,
                                              keyName,
                                              bisOriginal,
                                              omdOriginal)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            /** Upload the Thumbnail image to S3*/
            s3ThumbnailImageObject.setObjectContent(bisThumbnail);
            s3.putObject(new PutObjectRequest(BUCKET,
                                              keyName + THUMB_SUFFIX,
                                              bisThumbnail,
                                              omdThumbnail)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            // Get the URL for the original image as result
            result = s3.getUrl(BUCKET, keyName).toString();

        } catch(UnsupportedMediaTypeException umte){
            this.printErrorInfo(umte);
            throw new UnsupportedMediaTypeException(getErrorInfo(umte));

        }catch (AmazonServiceException ase) {
            this.printErrorInfo(ase);
            throw new IOException(getErrorInfo(ase));

        } catch (AmazonClientException ace) {
            this.printErrorInfo(ace);
            throw new IOException(getErrorInfo(ace));

        }  catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return result;
    }

    private byte[] getThumbnailBytes(byte[] imageBytes) throws IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        // Resize and get thumbnail image
        BufferedImage thumbnail = resizeImageToThumbnail(bis);
        // Create OutputStream and write BufferedImage to it
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumbnail, "jpg", baos);
        // Return byte array for thumbnail image
        return baos.toByteArray();
    }

    private BufferedImage resizeImageToThumbnail(ByteArrayInputStream bais) throws IOException{
        final int TARGET_WIDTH = 800;
        final int TARGET_HEIGHT = 400;

        BufferedImage image = null;
        try{
            image = ImageIO.read(bais);
            int type=image.getType();
            BufferedImage resizedImage=new BufferedImage(TARGET_WIDTH,TARGET_HEIGHT,type);
            Graphics2D g=resizedImage.createGraphics();
            g.drawImage(image,0,0,TARGET_WIDTH,TARGET_HEIGHT,null);
            g.dispose();
            return resizedImage;

            /*
            int width = image.getWidth() % 2 == 0 ? image.getWidth() : image.getWidth()+1;
            int height = image.getHeight() % 2 == 0 ? image.getHeight() : image.getHeight()+1;

            image = Scalr.pad(image, 800, Color.GRAY);
            int newWidth = width;
            int newHeight = height;

            while(newHeight % TARGET_HEIGHT != 0){
                newHeight += 2;
            }
            while(newWidth % TARGET_WIDTH != 0 || newWidth < newHeight){
                newWidth += 2;
            }

            int start_x = (image.getWidth() - newWidth) / 2;
            int start_y = (image.getHeight() - newHeight) / 2;

            image = Scalr.crop(image, start_x, start_y, newWidth, newHeight);
            image = Scalr.resize(image, Scalr.Method.SPEED, TARGET_WIDTH, TARGET_HEIGHT);
            return image;*/
        }catch(IOException ioe){
            throw new IOException(ioe.getMessage());
        }
//        return image;
    }

    private ObjectMetadata getImageObjectMetadata(String contentType, String fileName, Integer contentLength){
        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentType(contentType);
        omd.setHeader(FILENAME_HEADER, fileName);
        omd.setContentLength(contentLength);
        return omd;
    }

    private void printErrorInfo(AmazonServiceException ase){
        System.out.println("AmazonServiceException! Your request made it to Amazon S3, but it was "
                + "rejected with an error response.");

        System.out.println("Error Message:    " + ase.getMessage());
        System.out.println("HTTP Status Code: " + ase.getStatusCode());
        System.out.println("AWS Error Code:   " + ase.getErrorCode());
        System.out.println("Error Type:       " + ase.getErrorType());
        System.out.println("Request ID:       " + ase.getRequestId());
        System.out.println(ase.getMessage());
    }

    private String getErrorInfo(AmazonServiceException ase){
        String error_msg = "AmazonServiceException! Your request made it to Amazon S3," +
                " but it was rejected with an error response.";
        error_msg += "Error Message: "    + ase.getMessage();
        error_msg += "HTTP Status Code: " + ase.getStatusCode();
        error_msg += "AWS Error Code: "   + ase.getErrorCode();
        error_msg += "Error Type: "       + ase.getErrorType();
        error_msg += "Request ID: "       + ase.getRequestId();

        return error_msg;
    }

    private void printErrorInfo(AmazonClientException ace){
        System.out.println("AmazonClientException! The client encountered an internal error while "
                + "trying to communicate with S3. This could be due to network issues.");
        System.out.println(ace.getMessage());
    }

    private String getErrorInfo(AmazonClientException ace){
        System.out.println(ace.getMessage());
        return "AmazonClientException! The client encountered an internal error while "
                + "trying to communicate with S3. This could be due to network issues.";
    }
}