export class Offer {
  public title: string;
  public additionalDescription: string;
  public ownerLogin: string;
  public apartmentNumber: number;
  public street: string;
  public city: string;
  public costPerMonth: number;
  public rent: number;
  public deposit: number;
  public roomNumber: number;
  public area: number;

  constructor(title: string, additionalDescription: string, ownerLogin: string,
              apartmentNumber: number, street: string, city: string, costPerMonth: number,
              rent: number, deposit: number, roomNumber: number, area: number) {
    this.title = title;
    this.additionalDescription = additionalDescription;
    this.ownerLogin = ownerLogin;
    this.apartmentNumber = apartmentNumber;
    this.street = street;
    this.city = city;
    this.costPerMonth = costPerMonth;
    this.rent = rent;
    this.deposit = deposit;
    this.roomNumber = roomNumber;
    this.area = area;
  }
}
