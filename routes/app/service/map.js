import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class ServiceAddMapRoute extends Route {
  @service store;
  async model() {
    console.log('inside router');
    //console.log(this.store.findAll('My'));
    return {
      sf: await this.store.findAll('serviceattr'),
      db: await this.store.findAll('servicetableattr'),
      map: await this.store.findAll('servicemap'),
    };
  }
}
