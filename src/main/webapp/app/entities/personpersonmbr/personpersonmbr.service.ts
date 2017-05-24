import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Personpersonmbr } from './personpersonmbr.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonpersonmbrService {

    private resourceUrl = 'api/personpersonmbrs';
    private resourceSearchUrl = 'api/_search/personpersonmbrs';

    constructor(private http: Http) { }

    create(personpersonmbr: Personpersonmbr): Observable<Personpersonmbr> {
        const copy = this.convert(personpersonmbr);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personpersonmbr: Personpersonmbr): Observable<Personpersonmbr> {
        const copy = this.convert(personpersonmbr);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Personpersonmbr> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(personpersonmbr: Personpersonmbr): Personpersonmbr {
        const copy: Personpersonmbr = Object.assign({}, personpersonmbr);
        return copy;
    }
}
