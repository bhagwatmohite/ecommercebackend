package com.ecommerce.backend.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.ecommerce.backend.entity.SliderImages;
import com.ecommerce.backend.repository.SliderImagesRepository;

@Service
public class SliderImagesService {
	
	@Autowired
	private SliderImagesRepository sliderImagesRepository;
	
	
    private final String UPLOAD_DIR = "src/main/resources/static/sliderImages/";
    
    public Optional<SliderImages> getSliderImagesById(Long id) {
        return sliderImagesRepository.findById(id);
    }

    public List<SliderImages> getAllSliderImages() {
        return sliderImagesRepository.findAll();
    }
    
    public SliderImages createSliderImages(SliderImages sliderImages, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            String image = saveImage(imageFile, fileName);
            sliderImages.setImage(image);
        }
        return sliderImagesRepository.save(sliderImages);
    }

	 //if the file is not present then we will ceate 
    private String saveImage(MultipartFile imageFile, String fileName) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueFileName = fileName;
        Path filePath = uploadPath.resolve(uniqueFileName);
        imageFile.transferTo(filePath);

        return uniqueFileName;
    }
    
    //upadtae
    public SliderImages updateSliderImages(Long id, SliderImages updatedSliderImages, MultipartFile imageFile) throws IOException {
        Optional<SliderImages> optionalSliderImages = sliderImagesRepository.findById(id);
        if (optionalSliderImages.isPresent()) {
            SliderImages sliderImages = optionalSliderImages.get();
            // Update fields if they are present in updatedSliderImages
            if (updatedSliderImages.getDescription() != null) {
                sliderImages.setDescription(updatedSliderImages.getDescription());
            }
            // Update image file if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                String image = saveImage(imageFile, fileName);
                sliderImages.setImage(image);
            }
            return sliderImagesRepository.save(sliderImages);
        } else {
            throw new IllegalArgumentException("SliderImages with ID " + id + " not found");
        }
    }

    
   
	// for deleting 
    public boolean deleteSliderImagesById(Long id) {
        Optional<SliderImages> sliderImages = sliderImagesRepository.findById(id);
        if (sliderImages.isPresent()) {
        	sliderImagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
    


}
