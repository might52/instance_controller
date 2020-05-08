import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";
import {Function, Instance} from "./models/Instance";
import {log} from "util";


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
      log(`${operation} failed: ${error.message}`);
      try {
        return JSON.parse(error)
      } catch (error) {
        error("Response doesn't have JSON format");
        return of(result as T);
      }
    };
  }

  /**
   * Return list of {@link Instance} Instances.
   */
  getInstances(): Observable<Array<Instance>> {
    const url = `${this.compute_url}/all`;
    return this
      .httpClient
      .get<Array<Instance>>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => log('Fetching instances')),
        catchError(this.handleError<Array<Instance>>('getInstances', []))
      );
  }

  /**
   * Return the {@link Instance} by Id.
   * @param functionId function id.
   */
  getInstance(functionId: number): Observable<Instance> {
    const url = `${this.compute_url}/${functionId}`;
    return this
      .httpClient
      .get<Instance>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => log('Fetching instance by Id')),
        catchError(this.handleError<Array<Instance>>('getInstance by Id', []))
      )
  }

  saveInstance(func:Function): void {
    const url = `${this.compute_url}/`;
    log(`FunctionID ${func.id} and url: ${url}`);
    this
      .httpClient
      .post(url, func)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `Save function, function id: ${func.id}`
          )
        ),
        catchError(this.handleError<any>('saveFunction'))
      ).subscribe();
  }

  createNewInstance(functionId: number): void {
    const url = `${this.compute_url}/instantiate/${functionId}`;
    log(`FunctionID ${functionId} and url: ${url}`);
    this
      .httpClient
      .post(url, "")
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `Instantiate new instance of function, function id: ${functionId}`
          )
        ),
        catchError(this.handleError<any>('saveFunction'))
      ).subscribe();
  }

  reInstantiateInstance(serverId: number) {
    const url = `${this.compute_url}/instantiate/reinstantiate/${serverId}`;
    log(`ReInstantiate instance(server id): ${serverId} and url: ${url}`);
    this
      .httpClient
      .post(url, "")
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `ReInstantiate instance(server id): ${serverId}`
          )
        ),
        catchError(this.handleError<any>('ReInstantiate'))
      ).subscribe();
  }

  releaseInstance(serverId: number, functionId: number) {
    const url = `${this.compute_url}/instantiate/${functionId}/${serverId}`;
    log(`ServerId ${serverId}, FunctionId ${functionId} and url: ${url}`);
    this
      .httpClient
      .delete(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `Release instance(server id): ${serverId} of function: ${functionId}`
          )
        ),
        catchError(this.handleError<any>('releaseInstance'))
      ).subscribe();
  }

  releaseLastInstance(functionId: number) {
    const url = `${this.compute_url}/instantiate/${functionId}`;
    log(`Release FunctionId ${functionId} and url: ${url}`);
    this
      .httpClient
      .delete(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ =>
          log(
            `Release last instance of function: ${functionId}`
          )
        ),
        catchError(this.handleError<any>('releaseLastInstance'))
      ).subscribe();

  }
}



