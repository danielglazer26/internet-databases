import {FormBuilder, FormGroup, Validators} from '@angular/forms';

export class AnnouncementFormGroup extends FormGroup {
  constructor(fb: FormBuilder) {
    super({
      announcementId: fb.control(''),
      title: fb.control('',Validators.required),
      additionalDescription: fb.control('',Validators.required),
      coverPhotoId:fb.control(''),
      street: fb.control('', Validators.required),
      apartmentNumber: fb.control(''),
      province: fb.control(-1, Validators.pattern("[0-9+]")),
      city: fb.control('', Validators.required),
      announcementType: fb.control(0, Validators.required),
      ownerLogin:fb.control(''),
      costPerMonth: fb.control('', [Validators.required, Validators.pattern("[0-9]+")]),
      rent: fb.control('', [Validators.required, Validators.pattern("[0-9]+")]),
      area: fb.control('', [Validators.required, Validators.pattern("[0-9]+([.,]?[0-9]{1,2})*")]),
      roomNumber: fb.control('',  Validators.pattern("[0-9]+"))
    });
  }
}
