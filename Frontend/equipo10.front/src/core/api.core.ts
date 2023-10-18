import axios, {AxiosInstance, AxiosRequestConfig, AxiosResponse} from "axios";

class Api {
  protected api: AxiosInstance ;

  public constructor(config: AxiosRequestConfig) {
    this.api = axios.create(config);
  }

  /**
   *
   * @param config
   */
  public getUri(config?: AxiosRequestConfig): string {
    return this.api.getUri(config);
  }

  /**
   *
   * @param config
   */
  public request<T, R = AxiosResponse<T>>(config: AxiosRequestConfig): Promise<R> {
    return this.api.request(config);
  }

  /**
   *
   * @param url
   * @param config
   */
  public get<T, R = AxiosResponse<T>>(url: string, config?: AxiosRequestConfig): Promise<R> {
    return this.api.get(url, config);
  }

  /**
   *
   * @param url
   * @param config
   */
  public delete<T, R = AxiosResponse<T>>(url: string, config?: AxiosRequestConfig): Promise<R> {
    return this.api.delete(url, config);
  }

  /**
   *
   * @param url
   * @param config
   */
  public head<T, R = AxiosResponse<T>>(url: string, config?: AxiosRequestConfig): Promise<R> {
    return this.api.head(url, config);
  }

  /**
   *
   * @param url
   * @param data
   * @param config
   */
  public post<T, R = AxiosResponse<T>>(url: string, data?: T, config?: AxiosRequestConfig): Promise<R> {
    return this.api.post(url, data, config);
  }

  /**
   *
   * @param url
   * @param data
   * @param config
   */
  public put<T, R = AxiosResponse<T>>(url: string, data?: T, config?: AxiosRequestConfig): Promise<R> {
    return this.api.put(url, data, config);
  }

  /**
   *
   * @param url
   * @param data
   * @param config
   */
  public patch<T, R = AxiosResponse<T>>(url: string, data?: T, config?: AxiosRequestConfig): Promise<R> {
    return this.api.patch(url, data, config);
  }
  
}

export default Api;