import {Injectable} from '@angular/core';
import * as bcrypt from 'bcryptjs';

@Injectable({
  providedIn: 'root'
})
export class HashPasswordService {

  private salt = bcrypt.genSaltSync(10);

  constructor() {
  }

  makeHash(message: string): string {
    return bcrypt.hashSync(message, this.salt);
  }
}
