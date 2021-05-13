package com.example.gamesweb;

import java.util.Collection;
import java.util.Optional;

public class CateogryService {
    public Category getId(long id){return this.idCategory(id);}
    public Category setId(long id){
        return CategoryRepository.save(category);
    }

    public Collection<Category> getName(String name){return nameCategory;}
    public Collection<Category> setName(String name){this.nameCategory=name;}
    

}
