package org.jufe.erp.controller.rest.news;

import org.apache.log4j.Logger;
import org.jufe.erp.entity.News;
import org.jufe.erp.repository.Page;
import org.jufe.erp.service.news.NewsImageService;
import org.jufe.erp.service.news.NewsService;
import org.jufe.erp.utils.anno.AuthRequest;
import org.jufe.erp.utils.enums.AuthLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raomengnan on 2016/9/20.
 * 对News内容进行操作的rest接口
 * 允许普通用户进行，增删改操作，但是要在拦截器中进行对用户操作进行判断，是否只在自己写的文章上操作
 */
@RestController
@RequestMapping("/rest/news")
public class NewsRestController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsImageService newsImageService;

    private Logger logger = Logger.getLogger(NewsRestController.class);

    @RequestMapping("/get/{newsId}")
    public News getById(@PathVariable("newsId") String id){
        logger.debug("/get/" + id);
        if(id == null || id.isEmpty())
            return null;
        return newsService.findById(id);
    }

    @RequestMapping("/get-by-author/{author}")
    public List<News> getByAuthor(@PathVariable("author") String author){
        logger.debug("/get-by-author/" + author);
        if(author == null || author.isEmpty())
            return new ArrayList<>();

        return newsService.findByAuthor(author);
    }

    @RequestMapping("/get-by-title/{title}")
    public List<News> getByTitle(@PathVariable("title") String title){
        logger.debug("/get-by-title/" + title);
        if(title == null || title.isEmpty())
            return new ArrayList<>();

        return newsService.findByTitle(title);
    }

    @RequestMapping("/get-by-keyw/{keyw}")
    public List<News> getByKeyWord(@PathVariable("keyw") String keyw){
        logger.debug("/get-by-title/" + keyw);
        if(keyw == null || keyw.isEmpty())
            return new ArrayList<>();

        return newsService.findByKeyword(keyw);
    }

    @RequestMapping("/page/{pno}/{psize}")
    public Page<News> getPage(@PathVariable("pno") int pno, @PathVariable("psize") int psize){

        logger.debug("/page/{pno}/{psize}:" + pno + "," + "psize");
        return newsService.findPage(pno, psize);
    }

    @AuthRequest(level = AuthLevel.USER)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ModelMap> update(@RequestBody News news){
        logger.debug("update: " + news);
        boolean result = false;
        ModelMap map = new ModelMap();
        result = newsService.update(news);
        map.put("result", result);
        if(!result){
            map.put("msg", "更新出错，请重试");
            if(news == null || news.getId() == null)
                map.put("msg", "参数错误");
        }
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }

    @AuthRequest(level = AuthLevel.USER)
    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public ResponseEntity<ModelMap> add(@RequestBody News news){
        logger.debug("add: " + news);
        boolean result = false;
        ModelMap map = new ModelMap();
        result = newsService.addNews(news);
        map.put("result", result);
        if(!result){
            map.put("msg", "发布出错，请重试");
            if(news == null || news.getId() == null)
                map.put("msg", "参数错误");
        }
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }

    @AuthRequest(level = AuthLevel.USER)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<ModelMap> delete(@RequestBody String newsId, HttpServletRequest request){
        logger.debug("delete: " + newsId);
        boolean result = false;
        ModelMap map = new ModelMap();
        result = newsService.delete(newsId);
        map.put("result", result);
        if(result){
            newsImageService.deleteByNewsId(request.getSession().getServletContext().getRealPath("/"), newsId);
        }
        else {
            map.put("msg", "更新出错，请重试");
            if(newsId == null || newsId.isEmpty())
                map.put("msg", "参数错误");
        }
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }

}
