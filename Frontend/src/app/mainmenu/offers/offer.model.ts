export class Offer {
  public title: string;
  public additionalDescription: string;
  public announcementType: number;
  public ownerLogin: string;
  public apartmentNumber: number;
  public street: string;
  public city: string;
  public province: number;
  public costPerMonth: number;
  public rent: number;
  public deposit: number;
  public roomNumber: number;
  public area: number;

  constructor(title: string, additionalDescription: string, announcementType: number, ownerLogin: string,
              apartmentNumber: number, street: string, city: string, province: number, costPerMonth: number,
              rent: number, deposit: number, roomNumber: number, area: number) {
    this.title = title;
    this.additionalDescription = additionalDescription;
    this.announcementType = announcementType
    this.ownerLogin = ownerLogin;
    this.apartmentNumber = apartmentNumber;
    this.street = street;
    this.city = city;
    this.province = province
    this.costPerMonth = costPerMonth;
    this.rent = rent;
    this.deposit = deposit;
    this.roomNumber = roomNumber;
    this.area = area;
  }
}
