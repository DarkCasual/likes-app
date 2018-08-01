package ru.whalekit.likesapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.whalekit.likesapp.controller.response.GetLikesResponse;
import ru.whalekit.likesapp.controller.response.LikeResponse;
import ru.whalekit.likesapp.controller.response.Status;
import ru.whalekit.likesapp.service.LikeService;

@RestController
public class LikeController {
    private final LikeService service;

    @Autowired
    public LikeController(LikeService likeService) {
        service = likeService;
    }

    @GetMapping(value = "/like/{name}", produces = "application/json")
    public GetLikesResponse getLikes(@PathVariable("name") String name) {
        return new GetLikesResponse(name, service.getLikes(name));
    }

    @PostMapping(value = "/like/{name}", produces = "application/json")
    public LikeResponse like(@PathVariable("name") String name) {
        service.like(name);
        return new LikeResponse(Status.OK);
    }

}
