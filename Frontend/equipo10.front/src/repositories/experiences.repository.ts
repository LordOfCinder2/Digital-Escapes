import Axios, { AxiosRequestConfig  } from "axios";
import { mergeRight } from "ramda";
import Api from "../core/api.core";
import { TAddBooking, TAddExperience } from '../types/experiences';
import config from "../config/config";

export class ExperiencesRepository extends Api {

  protected readonly endpoints = {
    experiences: {
      search: 'experience/all',
      bySlug: 'experience/experienceSlug',
      allPaginated: 'experience/allPaginated',
      addExperience: 'experience',
      delete: 'experience/delete',
      byCategory: 'experience/experiencesByCategory',
    },
    categories:{
      search: 'category/all'
    },
    bookings:{
      datesByExp: 'reservation/datesByExp',
      submitBooking: 'reservation'
    }
  }

  constructor (baseOptions: AxiosRequestConfig = {}){
    super(mergeRight({baseURL:config.apiUrl}, baseOptions));
  }

  async search( token: string ){
    return this.get(this.endpoints.experiences.search,
      {
        headers: { Authorization: `Bearer ${token}` },
      },
    ).then((response) => response.data);
  }

  async getBySlug(slug: string){
    return this.get(this.endpoints.experiences.bySlug,
      { params: { slug }
    }).then((response) => response.data);
  }

  async getAllPaginated(page?: number){
    return this.get(this.endpoints.experiences.allPaginated,
      { params: { page }
    }).then((response) => response.data);
  }

  async getByCategory(categoryId: number, token: string){
    return this.get(this.endpoints.experiences.byCategory,
      {
        // headers: { Authorization: `Bearer ${token}` },
        params: { categoryId }
      },
    ).then((response) => response.data);
  }

  async searchCategories(){
    return this.get(this.endpoints.categories.search).then((response) => response.data);
  }

  async addExperience(body: TAddExperience){
    return this.post(this.endpoints.experiences.addExperience, 
      {...body}
    ).then((response) => response.data);
  }

  async deleteExp(id: number, token: string){
    return this.delete(this.endpoints.experiences.delete,
      {
        headers: { Authorization: `Bearer ${token}` },
        params: { id }
      },
    ).then((response) => response.data);
  }

  async getDatesByExp(expId: number, token: string){
    return this.get(this.endpoints.bookings.datesByExp,
      {
        // headers: { Authorization: `Bearer ${token}` },
        params: { expId }
      },
    ).then((response) => response.data);
  }

  async addBooking(body: TAddBooking, token: string){
    return this.post(this.endpoints.bookings.submitBooking,
      body,
      {
        headers: { Authorization: `Bearer ${token}` },
      },
    ).then((response) => response.data);
  }
}

export default new ExperiencesRepository();
