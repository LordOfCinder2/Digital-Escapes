import Axios, { AxiosRequestConfig  } from "axios";
import { mergeRight } from "ramda";
import Api from "../core/api.core";
import { TAddScore } from '../types/score';
import config from "../config/config";

export class ScoreRepository extends Api{

    protected readonly endpoints = {
        scores: {
            addScore: 'score',
            averageScore: 'score/average',
            allById: 'score/all'
        }     
    }

    constructor (baseOptions: AxiosRequestConfig = {}){
        super(mergeRight({baseURL:config.apiUrl}, baseOptions));
    }

    async getAverage(id: string, token: string) {
        return this.get(this.endpoints.scores.averageScore,
          {
            headers: { Authorization: `Bearer ${token}` },
            params: { id }
          },
        ).then((response) => response.data);
    }

    async addScore(body: TAddScore, token: string) {
        return this.post(this.endpoints.scores.addScore,{...body},
          {
            headers: { Authorization: `Bearer ${token}` }
          }
        ).then((response) => response.data);
    }

    async getScoresById(expId: number) {
        return this.get(this.endpoints.scores.allById,
          {
            params: { expId }
          },  
        ).then((response) => response.data);
    }
}

export default new ScoreRepository();