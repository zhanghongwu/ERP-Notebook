package org.jufe.erp.controller.rest.news;

import org.apache.log4j.Logger;
import org.jufe.erp.entity.NewsImage;
import org.jufe.erp.repository.Page;
import org.jufe.erp.service.news.NewsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Raomengnan on 2016/9/20.
 */
@RestController
@RequestMapping("/rest/news/image")
public class NewsImageRestController {

    @Autowired
    private NewsImageService service;

    private Logger logger = Logger.getLogger(NewsImageRestController.class);

    @RequestMapping("get/{pno}/{psize}")
    public Page<NewsImage> getPage(@PathVariable("pno") int pno, @PathVariable("psize") int psize){
        logger.debug(String.format("get/%s/%s", pno, psize));
        return service.getImagePage(pno, psize);
    }

    @RequestMapping("get-by-newsid/{newsid}")
    public List<NewsImage> getByNewsId(@PathVariable("newsid") String newsId){
        logger.debug("get-by-newsid/" + newsId);
        return service.getImageByNewsId(newsId);
    }

    @RequestMapping(value = "update-intro", method = RequestMethod.POST)
    public ResponseEntity<ModelMap> updateIntro(@RequestBody NewsImage newsImage){
        logger.debug("update-intro:" + newsImage);

        ModelMap map = new ModelMap();
        boolean result = false;
        if(newsImage == null || newsImage.getId() == null)
            map.put("msg", "参数错误");
        else {
            result = service.updateIntro(newsImage);
            if(!result)
                map.put("msg", "更新失败，请检查后重试");
        }

        map.put("result", result);
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "delete-by-url", method = RequestMethod.DELETE)
    public ResponseEntity<ModelMap> deleteByUrl(@RequestBody String url, HttpServletRequest request){
        logger.debug("deleteByUrl:" + url);
        ModelMap map = new ModelMap();
        boolean result = false;
        if(url == null)
            map.put("msg", "参数错误");
        else {
            result = service.deleteByUrl(request.getSession().getServletContext().getRealPath("/"),
                     url);
            if(!result)
                map.put("msg", "删除失败，请检查后重试");
        }

        map.put("result", result);
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "delete-by-newsid", method = RequestMethod.DELETE)
    public ResponseEntity<ModelMap> deleteByNewsId(@RequestBody String newsId, HttpServletRequest request){
        logger.debug("deleteByNewsId:" + newsId);
        ModelMap map = new ModelMap();
        boolean result = false;
        if(newsId == null)
            map.put("msg", "参数错误");
        else {
            result = service.deleteByNewsId(request.getSession().getServletContext().getRealPath("/"),
                    newsId);
            if(!result)
                map.put("msg", "删除失败，请检查后重试");
        }

        map.put("result", result);
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "upload-image", method = RequestMethod.PUT)
    public ResponseEntity<ModelMap> uploadNewsImage(NewsImage newsImage, MultipartFile imageFile, HttpServletRequest request){
        logger.debug("upload-news-image:" + newsImage + "," + imageFile);
        boolean result = false;
        ModelMap map = new ModelMap();
        if(newsImage.getNewsId() != null && imageFile != null){
            result = service.uploadImage(newsImage, imageFile,
                    request.getSession().getServletContext().getRealPath("/"));
            if(!result)
                map.put("msg", "上传失败，请检查后重试");
        }
        else
            map.put("msg", "参数错误，请检查");

        map.put("result", result);
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }


}
