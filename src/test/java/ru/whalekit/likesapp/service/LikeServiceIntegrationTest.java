package ru.whalekit.likesapp.service;


import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.whalekit.likesapp.dao.MongoPlayerDao;
import ru.whalekit.likesapp.domain.Player;
import ru.whalekit.likesapp.service.exception.PlayerNotFoundException;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LikeServiceIntegrationTest {
    private static final String DIMA = "dima";
    private static final String PETYA = "petya";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private LikeService service;

    @BeforeEach
    public void setUp() {
        Player dima = new Player(DIMA, 0);
        Player petya = new Player(PETYA, 0);
        mongoTemplate.insert(dima, MongoPlayerDao.COLLECTION_NAME);
        mongoTemplate.insert(petya, MongoPlayerDao.COLLECTION_NAME);
    }

    @AfterEach
    public void tearDown() {
        mongoTemplate.remove(new Query(), MongoPlayerDao.COLLECTION_NAME);
    }

    @Test
    @Tag("fast")
    public void basicLikeAndGetLikesTest() {
        service.like(DIMA);
        Assert.assertEquals(1, service.getLikes(DIMA));

        service.like(PETYA);
        Assert.assertEquals(1, service.getLikes(PETYA));
    }

    @Test
    @Tag("fast")
    public void likeNonExistentPlayerTest() {
        Executable executable = () -> service.like("NotFound");
        Assertions.assertThrows(PlayerNotFoundException.class, executable);
    }

    @Test
    @Tag("fast")
    public void getLikesNonExistentPlayerTest() {
        Executable executable = () -> service.getLikes("NotFound");
        Assertions.assertThrows(PlayerNotFoundException.class, executable);
    }

    @Test
    @Tag("slow")
    public void testConcurrency() throws InterruptedException {
        int N = 100000;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Object>> list = IntStream.range(0, N)
                .mapToObj(i -> (Runnable) () -> service.like(DIMA))
                .map(Executors::callable)
                .collect(Collectors.toList());
        executorService.invokeAll(list);

        Assert.assertEquals(N, service.getLikes(DIMA));
    }
}