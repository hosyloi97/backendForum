package com.bksoftware.controller.user.news;


import com.bksoftware.entities.Record;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.news.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/user/tag")
@RolesAllowed("ROLE_USER")
public class UserTagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<Set<Integer>> createTag(@RequestParam("content") String content,
                                                   HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Set<Integer> tagIds = new HashSet<>();
        System.out.println(content);
        Record record = recordService.findByName("tag");
        record.setSize(record.getSize() + 1);

        String[] nameTag = content.split("@");

        for (int i = 1; i < nameTag.length; i++) {
            nameTag[i].trim();
            System.out.println("tag: " + nameTag[i]);
        }

        List<Tag> tags = tagService.findAll();
        for (int i = 1; i < nameTag.length; i++) {
            final int[] index = {1};
            int iTag = i;
            tags.forEach(tag -> {
                if (nameTag[iTag].equals(tag.getName())) {
                    index[0] = -1;
                }
            });
            if (index[0] == 1) {
                Tag t = new Tag();
                t.setName(nameTag[i]);
                t.setStatus(true);
                tagService.saveTag(t);
                Tag tag = tagService.findByNameUnique(t.getName());
                tagIds.add(tag.getId());
            }else{
                Tag tag = tagService.findByNameUnique(nameTag[i]);
                tagIds.add(tag.getId());
            }
        }

        return new ResponseEntity<>(tagIds, HttpStatus.OK);
    }


}
