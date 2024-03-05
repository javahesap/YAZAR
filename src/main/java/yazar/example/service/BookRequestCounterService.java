package yazar.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BookRequestCounterService {

    
    
    private final RedisTemplate<String, Integer> redisTemplate;

    // Diğer alanlar
    @Value("${book.request.limit}")
    private int requestLimit;
    
    @Autowired
    public BookRequestCounterService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    
    


    public boolean isRequestLimitExceeded(String key) {
        Integer count = redisTemplate.opsForValue().get(key);
        if (count == null) {
            count = 0;
        }

        if (count >= requestLimit) {
            return true;
        }
        //her istekte kaçar kaçar artacak  1 er 1 er artacak
        redisTemplate.opsForValue().increment(key, 1);
        //iki daki arayla kilitlencek iki dakika sonra resetlem olacak
        redisTemplate.expire(key, 2, TimeUnit.MINUTES);

        return false;
    }
    
       public void resetRequestLimit(String key) {
        // Belirli bir anahtar altındaki sayacı sıfırla
        redisTemplate.opsForValue().set(key, 0);
    }
    
    
}

