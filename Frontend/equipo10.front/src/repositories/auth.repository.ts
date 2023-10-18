import { AxiosRequestConfig } from "axios";
import { mergeRight } from "ramda";
import Api from "../core/api.core";
import { TUser } from '../types/auth';
import config from "../config/config";

export class AuthRepository extends Api {

  protected readonly endpoints = {
    auth: {
      login: 'users/authenticate', 
      resendEmail: 'users/resendEmail'
    },
    signup: 'users/addNewUser',
    users: {
      allUsers: 'users/all',
      allInfo: 'users/user',
      deleteUser: 'users/delete',
      
    }
  }

  constructor(baseOptions: AxiosRequestConfig = {}) {
    super(mergeRight({ baseURL: config.apiUrl }, baseOptions));
  }

  async login(body: TUser) {
    return this.post(this.endpoints.auth.login,
      { ...body }
    ).then((response) => response.data);
  }

  async signUp(body: TUser) {
    return this.post(this.endpoints.signup, { ...body }).then((response) => response.data);
  }

  async getAllUsers(token: string) {
    return this.get(this.endpoints.users.allUsers,
      {
        headers: { Authorization: `Bearer ${token}` }
      },
    ).then((response) => response.data);
  }

  async getUserInfo(email: string, token: string) {
    return this.get(this.endpoints.users.allInfo,
      {
        headers: { Authorization: `Bearer ${token}` },
        params: { email }
      },
    ).then((response) => response.data);
  }

  async resendEmail(email: string) {
    return this.get(this.endpoints.auth.resendEmail, 
      {
        params: { email }
      }).then((response) => response.data);
  }
  
}

export default new AuthRepository();
