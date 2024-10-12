package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.infrastructures.responses.AnhSanPhamResponse;
import com.example.websitebanquanao.services.AnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AnhSanPhamController {
    @Autowired
    private AnhSanPhamService anhSanPhamService;

    @GetMapping("/get-anh/{id}")
    public List<AnhSanPhamResponse> getAnh(@PathVariable("id") UUID id) {
        List<AnhSanPhamResponse> listAnh = anhSanPhamService.getAll(id);
        return listAnh;
    }
}
