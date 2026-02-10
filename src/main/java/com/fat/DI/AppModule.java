package com.fat.DI;

import com.fat.BUS.Abstractions.Services.IAuthService;
import com.fat.BUS.Abstractions.Services.ICategoryService;
import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Abstractions.Services.IUploadImageService;
import com.fat.BUS.Services.AuthService;
import com.fat.BUS.Services.CategoryService;
import com.fat.BUS.Services.ProductService;
import com.fat.BUS.Services.UploadImageService;
import com.fat.DAO.Abstractions.Repositories.ICategoryDAO;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Repositories.CategoryDAO;
import com.fat.DAO.Repositories.ProductDAO;
import com.fat.GUI.MainForm;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {


        bind(MainForm.class).in(Singleton.class);
    }
}
