package org.example.minispringap12.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Caches the result of this method
    @Cacheable("users")
    public String getUser(String username) {
        return "Returning user data for " + username;
    }

    // Updates the cache for the user when this method is called
    @CachePut(value = "users", key = "#username")
    public String updateUser(String username, String newUserData) {
        // Simulate update logic
        return "Updated user data for " + username + " to " + newUserData;
    }

    // Removes the user from the cache
    @CacheEvict(value = "users", key = "#username")
    public void evictUserFromCache(String username) {
        // Simulate cache eviction
        System.out.println("Evicted user data for " + username + " from the cache");
    }
}
