import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Organization } from './organization.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OrganizationService {

    private resourceUrl = 'api/organizations';
    private resourceSearchUrl = 'api/_search/organizations';

    constructor(private http: Http) { }

    create(organization: Organization): Observable<Organization> {
        const copy = this.convert(organization);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(organization: Organization): Observable<Organization> {
        const copy = this.convert(organization);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Organization> {
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

    private convert(organization: Organization): Organization {
        const copy: Organization = Object.assign({}, organization);
        return copy;
    }
}
