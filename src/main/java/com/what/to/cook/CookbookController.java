package com.what.to.cook;

import com.what.to.cook.models.Cookbook;
import com.what.to.cook.models.User;
import com.what.to.cook.repositories.CookbookRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cookbook")
public class CookbookController {

    @Autowired
    CookbookRepository cookbookRepository;

    @Autowired
    AuthService authService;

    record CookbookRequest(String name) {}

    @PostMapping
    Integer create(
        @RequestBody CookbookRequest cookbookRequest,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        AggregateReference<User, Integer> userId = authService.getUserIdFromRequest(request, response).orElse(null);
        if (userId == null) {
            return null;
        }

        Cookbook cookbook = new Cookbook(null, cookbookRequest.name, userId, 0);
        cookbookRepository.save(cookbook);
        return cookbook.getId();
    }

    @GetMapping("/{cookbookId}")
    Cookbook get(@PathVariable Integer cookbookId, HttpServletResponse response) {
        Cookbook cookbook = cookbookRepository.findById(cookbookId).orElse(null);
        if (cookbook == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("message", "No cookbook with that id was found");
            return null;
        }

        return cookbook;
    }

    @DeleteMapping("/{cookbookId}")
    void delete(@PathVariable Integer cookbookId, HttpServletResponse response) {
        Cookbook cookbook = cookbookRepository.findById(cookbookId).orElse(null);
        if (cookbook == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("message", "No cookbook with that id was found");
            return;
        }

        cookbookRepository.delete(cookbook);
    }

    @PutMapping("/{cookbookId}")
    void update(
        @PathVariable Integer cookbookId,
        @RequestBody CookbookRequest cookbookRequest,
        HttpServletResponse response
    ) {
        if (cookbookRequest.name == null || cookbookId == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("message", "New name or cookbook id not supplied in request");
            return;
        }

        Cookbook cookbook = cookbookRepository.findById(cookbookId).orElse(null);
        if (cookbook == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("message", "No cookbook with that id was found");
            return;
        }

        cookbook.setName(cookbookRequest.name());
        cookbookRepository.save(cookbook);
    }
}
