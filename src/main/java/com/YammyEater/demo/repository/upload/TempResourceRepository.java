package com.YammyEater.demo.repository.upload;

import com.YammyEater.demo.domain.upload.TempResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempResourceRepository extends JpaRepository<TempResource, String> {
}
