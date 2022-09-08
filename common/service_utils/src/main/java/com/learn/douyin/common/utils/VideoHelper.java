package com.learn.douyin.common.utils;


import com.learn.model.video.MultipartFileDto;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class VideoHelper {
    /**
     * 获取指定视频的帧并保存为图片至指定目录
    // * @param videofile  源视频文件路径
     * @throws Exception
     * @throws IOException
     */
    public static MultipartFile fetchFrame(MultipartFile multipartFile)
            throws Exception, IOException {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        File file=null;
        try {
            file = File.createTempFile(fileName, prefix);
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //以上是转成file
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.start();
        String rotate =ff.getVideoMetadata("rotate");//视频的旋转角度
        int length = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < length) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabImage(); //应该使用grabImage而不是grabFrame，这样可以避免mov格式的视频截图失败
            if ((i < 5) && (f.image != null)) {
                i++;
                continue;
            }
            IplImage src = null;
            if(null !=rotate &&rotate.length() > 1) {
                OpenCVFrameConverter.ToIplImage converter =new OpenCVFrameConverter.ToIplImage();
                src =converter.convert(f);
                f =converter.convert(rotate(src, Integer.valueOf(rotate)));
            }
            MultipartFile image = doExecuteFrame(f);
            return image;
        }
        return null;
    }


    public static IplImage rotate(IplImage src,int angle) {
        IplImage img = IplImage.create(src.height(),src.width(),src.depth(),src.nChannels());
        opencv_core.cvTranspose(src,img);
        opencv_core.cvFlip(img,img,angle);
        return img;
    }


    public static MultipartFile doExecuteFrame(Frame f) {
        if (null ==f ||null ==f.image) {
            return null;
        }
        MultipartFile multipartFile=null;
        try {
            Java2DFrameConverter converter =new Java2DFrameConverter();
            BufferedImage bi =converter.getBufferedImage(f);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bi, "jpg", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            multipartFile=new MultipartFileDto("image", "image.jpg", "text/plain", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartFile;
    }

}
