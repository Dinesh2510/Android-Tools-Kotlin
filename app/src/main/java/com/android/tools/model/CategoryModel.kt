package com.android.tools.model

class CategoryModel {
    private var category_image: String? = null

    private var category_name: String? = null

    private var id: String? = null

    fun getCategory_image(): String? {
        return category_image
    }

    fun setCategory_image(category_image: String?) {
        this.category_image = category_image
    }

    fun getCategory_name(): String? {
        return category_name
    }

    fun setCategory_name(category_name: String?) {
        this.category_name = category_name
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }
}