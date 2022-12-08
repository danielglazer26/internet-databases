import {Injectable} from '@angular/core';
import * as bcrypt from 'bcryptjs';

@Injectable({
  providedIn: 'root'
})
export class HashPasswordService {

  constructor() {
  }

  makeHash(message: string): string {
    return bcrypt.hashSync(message, "$2a$10$T37ajL7I4YSOrs.2h6Vvi.fLQefjuAHmOxbf2NKlc1afev/g/AYi2");
  }
}
