import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";
import {Function, Instance} from "./models/Instance";


@Injectable({
  providedIn: 'root'
})
export class InstanceService {

  instance_url = "http://localhost:8008";
  compute_url = `${this.instance_url}/api/v1/function`;

  httpOptions = {
    headers:
      new HttpHeaders({
        'Content-Type': 'application/json'
      })
  };

  constructor(private httpClient: HttpClient) {
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.log(`${operation} failed: ${error.message}`);
      try {
        return JSON.parse(error)
      } catch (error) {
        console.error("Response doesn't have JSON format");
        return of(result as T);
      }
    };
  }

  getInstances(): Observable<Array<Instance>> {
    const url = `${this.compute_url}/all`;
    return this
      .httpClient
      .get<Array<Instance>>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => console.log('Fetching instances')),
        catchError(this.handleError<Array<Instance>>('getInstances', []))
      );
  }

  getInstance(id: number): Observable<Instance> {
    const url = `${this.compute_url}/${id}`;
    return this
      .httpClient
      .get<Instance>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => console.log('Fetching instance by Id')),
        catchError(this.handleError<Array<Instance>>('getInstance by Id', []))
      )
  }

  saveInstance(id: number, func:Function): void {
    const url = `${this.compute_url}/${id}`;
    console.log(`FunctionID ${id} and url: ${url}`);
    this
      .httpClient
      .post(url, func)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          console.log(
            `Save function, function id: ${id}`
          )
        ),
        catchError(this.handleError<any>('saveFunction'))
      ).subscribe();
  }

}



