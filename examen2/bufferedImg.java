
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO 720
 */
public class bufferedImg {
    String path="", name="";
    public bufferedImg(String path, String name){
        this.name=name;
        this.path=path;
       
    }
    public BufferedImage bi() throws IOException{
        return ImageIO.read(getClass().getResource(path + name));
    }
}
