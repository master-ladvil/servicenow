import Model, { attr } from '@ember-data/model';

export default class ServiceuserModel extends Model {
  @attr country;
  @attr homephone;
  @attr employeenumber;
  @attr gender;
  @attr city;
  @attr timezone;
  @attr roles;
  @attr title;
  @attr phone;
  @attr street;
  @attr name;
  @attr preferredlanguage;
  @attr email;
  @attr username;
}
