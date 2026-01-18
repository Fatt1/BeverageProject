package com.fat.DI;

import com.fat.BUS.Abstractions.Services.ICategoryService;
import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Services.CategoryService;
import com.fat.BUS.Services.ProductService;
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
        // Bind your services
        // Giống: services.AddTransient<IAuthService, AuthService>();
        bind(IProductService.class).to(ProductService.class);
        bind(ICategoryService.class).to(CategoryService.class);

        // Bind DAO
        bind(ICategoryDAO.class).to(CategoryDAO.class);
        bind(IProductDAO.class).to(ProductDAO.class);

        bind(MainForm.class).in(Singleton.class);
    }
}
