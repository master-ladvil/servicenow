import Model, { attr } from '@ember-data/model';

export default class MapModel extends Model {
  @attr attrname;
  @attr dbattrname;
}
