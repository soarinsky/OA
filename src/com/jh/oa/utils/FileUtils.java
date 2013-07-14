package com.jh.oa.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/** 
 * �ļ��������߰�
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class FileUtils 
{
	private static String SDPATH = Environment.getExternalStorageDirectory() + File.separator;
	/**
	 * д�ı��ļ�
	 * ��Androidϵͳ�У��ļ������� /data/data/PACKAGE_NAME/files Ŀ¼��
	 * @param context
	 * @param msg
	 */
	public static void write(Context context, String fileName, String content) 
	{ 
		if( content == null )	content = "";
		
		try 
		{
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write( content.getBytes() ); 
			
			fos.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
	
	/**
	 * ��ȡ�ı��ļ�
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read( Context context, String fileName ) 
	{
		try 
		{
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	} 
	
	
	
	private static String readInStream(FileInputStream inStream)
	{
		try 
		{
		   ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		   byte[] buffer = new byte[512];
		   int length = -1;
		   while((length = inStream.read(buffer)) != -1 )
		   {
			   outStream.write(buffer, 0, length);
		   }
		   
		   outStream.close();
		   inStream.close();
		   return outStream.toString();
		} 
		catch (IOException e)
		{
		   Log.i("FileTest", e.getMessage()); 
		}
		return null;
	}
	
	public static File createFile( String folderPath, String fileName )
	{
		File destDir = new File(folderPath);
		if (!destDir.exists()) 
		{
			destDir.mkdirs();
		}
		return new File(folderPath,  fileName + fileName );
	}
	
	/**
	 * ���ֻ�дͼƬ
	 * @param buffer   
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile( byte[] buffer, String folder, String fileName )
	{
		boolean writeSucc = false;
		
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		
		String folderPath = "";
		if( sdCardExist )
		{
			folderPath = Environment.getExternalStorageDirectory() + File.separator +  folder + File.separator;
		}
		else
		{
			writeSucc =false;
		}
		
		File fileDir = new File(folderPath);
		if(!fileDir.exists()) 
		{
			fileDir.mkdirs();
		}
		  
		File file = new File( folderPath + fileName );
		FileOutputStream out = null;
		try 
		{
			out = new FileOutputStream( file );
			out.write(buffer);
			writeSucc = true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try {out.close();} catch (IOException e) {e.printStackTrace();}
		}
		
		return writeSucc;
	}
	
	/**
	 * �����ļ�����·����ȡ�ļ���
	 * @param filePath
	 * @return
	 */
	public static String getFileName( String filePath )
	{
		if( StringUtils.isEmpty(filePath) )	return "";
		return filePath.substring( filePath.lastIndexOf( File.separator )+1 );
	}
	/**
	 * �����ļ��ľ���·����ȡ�ļ�������������չ��
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat( String filePath){
		if(StringUtils.isEmpty(filePath)){
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator)+1,point);
	}
	
	/**
	 * ��ȡ�ļ���չ��
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat( String fileName )
	{
		if( StringUtils.isEmpty(fileName) )	return "";
		
		int point = fileName.lastIndexOf( '.' );
		return fileName.substring( point+1 );
	}
	
	/**
	 * ��ȡ�ļ���С
	 * @param filePath
	 * @return
	 */
	public static long getFileSize( String filePath )
	{
		long size = 0;
		
		File file = new File( filePath );
		if(file!=null && file.exists())
		{
			size = file.length();
		} 
		return size;
	}
	
	/**
	 * ��ȡ�ļ���С
	 * @param size �ֽ�
	 * @return
	 */
	public static String getFileSize(long size) 
	{
		if (size <= 0)	return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float)size / 1024;
		if (temp >= 1024) 
		{
			return df.format(temp / 1024) + "M";
		}
		else 
		{
			return df.format(temp) + "K";
		}
	}

	/**
	 * ת���ļ���С
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

	/**
	 * ��ȡĿ¼�ļ���С
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
	    if (!dir.isDirectory()) {
	    	return 0;
	    }
	    long dirSize = 0;
	    File[] files = dir.listFiles();
	    for (File file : files) {
	    	if (file.isFile()) {
	    		dirSize += file.length();
	    	} else if (file.isDirectory()) {
	    		dirSize += file.length();
	    		dirSize += getDirSize(file); //�ݹ���ü���ͳ��
	    	}
	    }
	    return dirSize;
	}
	
	/**
	 * ��ȡĿ¼�ļ�����
	 * @param f
	 * @return
	 */
	public long getFileList(File dir){
        long count = 0;
        File[] files = dir.listFiles();
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
            	count = count + getFileList(file);//�ݹ�
            	count--;
            }
        }
        return count;  
    }
	
	public static byte[] toBytes(InputStream in) throws IOException 
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    int ch;
	    while ((ch = in.read()) != -1)
	    {
	    	out.write(ch);
	    }
	    byte buffer[]=out.toByteArray();
	    out.close();
	    return buffer;
	}
	
	/**
	 * ����ļ��Ƿ����
	 * @param name
	 * @return
	 */
	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;

	}
	
	/**
	 * ����SD����ʣ��ռ�
	 * @return ����-1��˵��û�а�װsd��
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}

	/**
	 * �½�Ŀ¼
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectory(String directoryName) {
		boolean status;
		if (!directoryName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + directoryName);
			status = newPath.mkdir();
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * ����Ƿ�װSD��
	 * @return
	 */
	public static boolean checkSaveLocationExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * ɾ��Ŀ¼(������Ŀ¼��������ļ�)
	 * @param fileName
	 * @return
	 */
	public static boolean deleteDirectory(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then
				// delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					Log.i("DirectoryManager deleteDirectory", fileName);
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * ɾ���ļ�
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					Log.i("DirectoryManager deleteFile", fileName);
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}
	
	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 */
	public static boolean isFileExist(String fileName){
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	
	/**
	 * ��һ��InputStream���������д�뵽SD����
	 */
	public static File write2SD(String content, String filename){
		return write2SD(content,"bus",filename);
	}
	
	/**
	 * ��һ��InputStream���������д�뵽SD����
	 */
	public static File write2SD(String content, String path,String filename){
		File file = null;
		BufferedWriter bw = null;
	
		try {
			//����ļ��в����ڣ��򴴽��ļ��� 
			if( !isFileExist(path) ){
				createSDDir(path);
			}
			//����ļ������ڣ��򴴽��ļ�
			if(!isFileExist(path + File.separator + filename)){
				file = createSDFile(path + File.separator + filename);
			}
			file = new File(SDPATH + path + File.separator + filename);
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Error", "д�ļ�����!--->OperateSD.write2SD()");
		} 
		return file;
	}
	
	/**
	 * ��SD���ж�������
	 */
    public static String readSD(String path)
    {
    	StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		FileInputStream input = null;
		try{
			File f=new File(SDPATH+path);
			input = new FileInputStream(f);
			buffer = new BufferedReader(new InputStreamReader(input));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
			buffer.close();
			input.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
    	return sb.toString();
    }

    /**
	 * ��һ��InputStream���������д�뵽SD����
	 */
	public static File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			while((input.read(buffer)) != -1){
				output.write(buffer);
			}
			output.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
}