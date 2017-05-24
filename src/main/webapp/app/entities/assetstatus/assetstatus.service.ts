import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Assetstatus } from './assetstatus.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AssetstatusService {

    private resourceUrl = 'api/assetstatuses';
    private resourceSearchUrl = 'api/_search/assetstatuses';

    constructor(private http: Http) { }

    create(assetstatus: Assetstatus): Observable<Assetstatus> {
        const copy = this.convert(assetstatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(assetstatus: Assetstatus): Observable<Assetstatus> {
        const copy = this.convert(assetstatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Assetstatus> {
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

    private convert(assetstatus: Assetstatus): Assetstatus {
        const copy: Assetstatus = Object.assign({}, assetstatus);
        return copy;
    }
}
