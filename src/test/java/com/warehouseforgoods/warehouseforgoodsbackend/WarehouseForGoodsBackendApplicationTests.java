package com.warehouseforgoods.warehouseforgoodsbackend;

import com.google.gson.Gson;
import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;
import com.warehouseforgoods.warehouseforgoodsbackend.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class WarehouseForGoodsBackendApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;

    @Test
    public void productListTest() throws Exception{
        this.mockMvc
                .perform(get("/product"))
                .andExpect(status().isOk());
    }
    @Test
    public void saveProductTest() throws Exception{
        Product product = new Product(1L,"first");
        Gson gson = new Gson();
        String jsonString = gson.toJson(product);

        this.mockMvc
                .perform(post("/product").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateProductTest() throws Exception{
        Product product = new Product(1L,"second");
        Gson gson = new Gson();
        String jsonString = gson.toJson(product);

        this.mockMvc
                .perform(put("/product").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductTest() throws Exception{
        this.mockMvc.perform(delete("/product/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
