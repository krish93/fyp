package fyp;
import abe.Cipher;
import abe.Private;
import abe.Public;
import abe.SerializeFile;
import abe.AttributeBasedEncryption;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import cpabe.Common;
import cpabe.Cpabe;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
public class upload {
	
	private static final String SUFFIX = "/";
        static AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAIZK2WBJDOY3JBNTA", 
				"NqvK+7LTVZmQCWs49KeB0urJdoVYH0Jy2GR84Btv");
	upload(){}
	public static void main(String[] args) {
            
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess for 
		// this example to work
		/*AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAIZK2WBJDOY3JBNTA", 
				"NqvK+7LTVZmQCWs49KeB0urJdoVYH0Jy2GR84Btv");
		
		// create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		
		// create bucket - name must be unique for all S3 users
		String bucketName = "cp-abe";
		//s3client.createBucket(bucketName);
		
		// list buckets
		for (Bucket bucket : s3client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		
		// create folder into bucket
		String folderName = "testfolder";
		createFolder(bucketName, folderName, s3client);
		
		// upload file to folder and set it to public
		String fileName = folderName + SUFFIX + "abacus.jpeg";
		s3client.putObject(new PutObjectRequest(bucketName, fileName, 
				new File("C:\\Users\\Harikrish\\Downloads\\abacus.jpg"))
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		//deleteFolder(bucketName, folderName, s3client);
		
		// deletes bucket
		//s3client.deleteBucket(bucketName);*/
                
	}
	public static int uploadKeyFile(String file_name,String file_path)
        {
            System.out.println("upload file called = ");
            System.out.println("file_path = " + file_path);
            try
            {
		TransferManager tx = new TransferManager(credentials);
		AmazonS3 s3client = new AmazonS3Client(credentials);
		String bucketName = "cp-abe";
                String folderName = "Policy";
                String fileName = folderName + SUFFIX + file_name;
		for (Bucket bucket : s3client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		final Upload myUpload=tx.upload(bucketName, fileName,new File(file_path));
                System.out.println("myUpload = " + myUpload.isDone());
                System.out.println("Transfer: " + myUpload.getDescription());
                System.out.println("  - State: " + myUpload.getState());
                int status=0;
                while (myUpload.isDone() == false) {
                    double value=myUpload.getProgress().getPercentTransferred();
                    status = (int)value;
                   // System.out.println(myUpload.getProgress().getPercentTransferred());
                }
                double value=myUpload.getProgress().getPercentTransferred();
                status = (int)value;
                System.out.println("  - State: " + myUpload.getState());
                String file_status=myUpload.getState().toString().toLowerCase();
                myUpload.waitForCompletion();
                tx.shutdownNow();
                
               if( file_status== "complete")
                   return 1;
            }
            catch(Exception evt)
            {
                System.out.println("upload = " + evt);
            }
            return 0;
        }
        public static int uploadFile(String file_name,String file_path)
        {
            System.out.println("upload file called = ");
            System.out.println("file_path = " + file_path);
            try
            {
		TransferManager tx = new TransferManager(credentials);
		AmazonS3 s3client = new AmazonS3Client(credentials);
		String bucketName = "cp-abe";
                String folderName = "File";
                String fileName = folderName + SUFFIX + file_name;
		for (Bucket bucket : s3client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		final Upload myUpload=tx.upload(bucketName, fileName,new File(file_path));
                System.out.println("myUpload = " + myUpload.isDone());
                System.out.println("Transfer: " + myUpload.getDescription());
                System.out.println("  - State: " + myUpload.getState());
                int status=0;
                
                while (myUpload.isDone() == false) {
                    double value=myUpload.getProgress().getPercentTransferred();
                    status = (int)value;
                    //System.out.println(myUpload.getProgress().getPercentTransferred());
                
                }
                double value=myUpload.getProgress().getPercentTransferred();
                
                status = (int)value;
                System.out.println("  - State: " + myUpload.getState());
                myUpload.waitForCompletion();
                tx.shutdownNow();
               if(status==100)
                   return 1;
            }
            catch(Exception evt)
            {
                System.out.println("upload = " + evt);
            }
            return 0;
        }
	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + SUFFIX, emptyContent, metadata);
		client.putObject(putObjectRequest);
	}
        
        public static List ListFile()
        {
            List files=new ArrayList();
        try {
            AmazonS3 s3client = new AmazonS3Client(credentials);
            
            ObjectListing objects = s3client.listObjects("cp-abe");
            do {
                    for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                        if(objectSummary.getSize()>0&&objectSummary.getKey().contains("File"))
                        {
                        files.add(objectSummary.getKey().toString().split("/")[1]);
                            System.out.println("files = " + objectSummary.getKey());
                        System.out.println(objectSummary.getKey() + "\t" +
                         objectSummary.getSize() + "\t" +
                          StringUtils.fromDate(objectSummary.getLastModified()));
                        }
                    }
                    objects = s3client.listNextBatchOfObjects(objects);
                } while (objects.isTruncated());
            
            
        } catch (Exception ex) {
            Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return files;
        }
        public static int decryptFile(List file,String key)
        {
            Iterator iter=file.iterator();
            while(iter.hasNext())
            {
                String name=(String)iter.next();
            String file_dir=System.getProperty("user.dir")+"\\download-encrypt\\"+name;
            String key_dir=System.getProperty("user.dir")+"\\download-key\\"+key;
            String dir=System.getProperty("user.dir");
            String pub=dir+"\\sample1";
            String msk=dir+"\\sample2";
            String decrypt_dir=dir+"\\download";
            try
            {
                Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("attrib +H "+file_dir); 
            int exitVal = proc.exitValue();
            proc.destroy();
            
            }
            catch(Exception e)
            {
                
            }
            try
            {
                Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("attrib +H "+key_dir); 
            int exitVal = proc.exitValue();
            proc.destroy();
            }
            catch(Exception e)
            {
                
            }
            File f=new File(decrypt_dir);
            if(!f.exists())
            {
                f.mkdirs();
            }
            String decrypt=decrypt_dir+"\\"+name;
            Cpabe dec=new Cpabe();
            dec.setup(pub,msk);
            int ret=dec.decryption(pub, key_dir, file_dir, decrypt,dir+"\\sample3");
            File del_f=new File(file_dir);
            if(del_f.exists())
            {
                del_f.delete();
            }
            File del_key=new File(key_dir);
            if(del_key.exists())
            {
                del_key.delete();
            }
            File samp=new File(pub);
            if(samp.exists())
            {
                samp.delete();
            }
            File samp1=new File(msk);
            if(samp1.exists())
            {
                samp1.delete();
            }
            if(ret==1)
            {
                return 1;
            }
            
            }
            return 0;
        }
        public static void downloadKeyFile(String key_file)
        {
            try
            {
            AmazonS3 s3client = new AmazonS3Client(credentials);
            String existingBucketName = "cp-abe";
            String workingDir = System.getProperty("user.dir");
	    System.out.println("Current working directory : " + workingDir);
            String keyName = "Policy/"+key_file;
            String file_path=workingDir+"\\download-key";
            System.out.println("file_path = " + file_path);
                System.out.println("keyName = " + keyName);
            File file=new File(file_path);
            if(!file.exists())
            {
 
                file.mkdirs();
            }
            String new_working_dir=workingDir.replace("\\","//");
            String name[]=keyName.split("/");
            new_working_dir=new_working_dir+"//download-key";
            System.out.println("new_working_dir = " + new_working_dir);
            System.out.println("name[1] = " + name[1]);
            System.out.println("keyName = " + keyName);
            GetObjectRequest request = new GetObjectRequest(existingBucketName,keyName);
            S3Object object = s3client.getObject(request);
            S3ObjectInputStream objectContent = object.getObjectContent();
            
            System.out.println(IOUtils.copy(objectContent, new FileOutputStream(new_working_dir+"//"+name[1])));   
            
            }
            catch(Exception e)
            {
                System.out.println("download error = " + e);
            }
            
        }
        public static void downloadFile(List files)
        {
            try
            {
            AmazonS3 s3client = new AmazonS3Client(credentials);
            String existingBucketName = "cp-abe";
            String workingDir = System.getProperty("user.dir");
	    System.out.println("Current working directory : " + workingDir);
            Iterator iter=files.iterator();
            while(iter.hasNext())
            {
            String keyName = "File/"+(String)iter.next();
            String file_path=workingDir+"\\download-encrypt";
            System.out.println("file_path = " + file_path);
                System.out.println("keyName = " + keyName);
            File file=new File(file_path);
            if(!file.exists())
            {
 
                file.mkdirs();
            }
            String new_working_dir=workingDir.replace("\\","//");
            String name[]=keyName.split("/");
            new_working_dir=new_working_dir+"//download-encrypt";
            System.out.println("new_working_dir = " + new_working_dir);
            System.out.println("name[1] = " + name[1]);
            System.out.println("keyName = " + keyName);
            GetObjectRequest request = new GetObjectRequest(existingBucketName,keyName);
            S3Object object = s3client.getObject(request);
            S3ObjectInputStream objectContent = object.getObjectContent();
            
            System.out.println(IOUtils.copy(objectContent, new FileOutputStream(new_working_dir+"//"+name[1])));   
            }
            }
            catch(Exception e)
            {
                System.out.println("download error = " + e);
            }
        }
        
}