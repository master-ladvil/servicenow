import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
export default class AppServiceAddRoute extends Route {
    @service store;
  async model() {
    console.log('inside router');
    //console.log(this.store.findAll('My'));
    return {
      role: await this.store.findAll('servicerole'),
    };
  }
}
