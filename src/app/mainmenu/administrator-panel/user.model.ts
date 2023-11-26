export class User {
  public personId: number;
  public login: string;
  public email: string;
  public name: string;
  public surname: string;
  public nip: string;
  public phoneNumber: string;
  public role: string;


  constructor(personId: number, login: string, email: string, name: string, surname: string, nip: string, phoneNumber: string, role: string) {
    this.personId = personId;
    this.login = login;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.nip = nip;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }
}
