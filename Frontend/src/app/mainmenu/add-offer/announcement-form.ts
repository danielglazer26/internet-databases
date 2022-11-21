import {FormBuilder, FormGroup, Validators} from '@angular/forms';

export class AnnouncementFormGroup extends FormGroup {
  constructor(fb: FormBuilder) {
    super({
      description: fb.control(''),
      name: fb.control('', [Validators.required, Validators.maxLength(30)]),
      surname: fb.control('', Validators.required),
      login: fb.control('', Validators.required),
      email: fb.control('', Validators.email),
      street: fb.control('', Validators.required),
      apartmentNumber: fb.control(''),
      province: fb.control('', Validators.required),
      city: fb.control('', Validators.required),
      announcementType: fb.control('', Validators.required),
      cost: fb.control('', Validators.required),
      rent: fb.control('', [Validators.required, Validators.pattern("[0-9]+")]),
      area: fb.control('', [Validators.required, Validators.pattern("[0-9]+([.,]?[0-9]{1,2})")]),
      roomNumber: fb.control('', [Validators.required, Validators.pattern("[0-9]+")])
    });
  }
}
