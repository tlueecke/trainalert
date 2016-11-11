import { WatcherConfigUiPage } from './app.po';

describe('watcher-config-ui App', function() {
  let page: WatcherConfigUiPage;

  beforeEach(() => {
    page = new WatcherConfigUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
