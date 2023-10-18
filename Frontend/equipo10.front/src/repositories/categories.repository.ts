import Axios, { AxiosRequestConfig  } from "axios";
import { mergeRight } from "ramda";
import Api from "../core/api.core";
import { TAddCategory } from '../types/categories';
import config from "../config/config";

export class CategoriesRepository extends Api{

    protected readonly endpoints = {
        categories: {
            allCategories: 'category/all',
            addCategory: 'category'
        }     
    }

    constructor (baseOptions: AxiosRequestConfig = {}){
        super(mergeRight({baseURL:config.apiUrl}, baseOptions));
    }

    async getAllCategories(){
        return this.get(this.endpoints.categories.allCategories).then((response) => response.data);
    }

    async addCategory(body: TAddCategory){
        return this.post(this.endpoints.categories.addCategory, {...body}).then((response) => response.data);
    }
}

export default new CategoriesRepository();