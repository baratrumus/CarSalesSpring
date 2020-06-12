package carsale.service;

import org.springframework.web.multipart.MultipartFile;

public class FormDataWithFile {

        private String brands;
        private String models;
        private String body;
        private String engine;
        private String mileage;
        private String caryear;
        private String color;
        private MultipartFile file;
        private String price;
        private String description;
        private Boolean sold;

        public FormDataWithFile(String brands, String models, String body, String engine,
                                String mileage, String caryear, String color, MultipartFile file,
                                String price, String description, Boolean sold) {
                this.brands = brands;
                this.models = models;
                this.body = body;
                this.engine = engine;
                this.mileage = mileage;
                this.caryear = caryear;
                this.color = color;
                this.file = file;
                this.price = price;
                this.description = description;
                this.sold = sold;
        }

        public FormDataWithFile() {
        }

        public Boolean getSold() {
                return sold;
        }

        public void setSold(Boolean sold) {
                this.sold = sold;
        }

        public String getBrands() {
                return brands;
        }

        public void setBrands(String brands) {
                this.brands = brands;
        }

        public String getModels() {
                return models;
        }

        public void setModels(String models) {
                this.models = models;
        }

        public String getBody() {
                return body;
        }

        public void setBody(String body) {
                this.body = body;
        }

        public String getEngine() {
                return engine;
        }

        public void setEngine(String engine) {
                this.engine = engine;
        }

        public String getMileage() {
                return mileage;
        }

        public void setMileage(String mileage) {
                this.mileage = mileage;
        }

        public String getCaryear() {
                return caryear;
        }

        public void setCaryear(String caryear) {
                this.caryear = caryear;
        }

        public String getColor() {
                return color;
        }

        public void setColor(String color) {
                this.color = color;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public MultipartFile getFile() {
                return file;
        }

        public void setFile(MultipartFile file) {
                this.file = file;
        }

}
