package igeorge.xtreme.ergotechnicaltest.presenters;

import igeorge.xtreme.ergotechnicaltest.server.APIInteractor;

/**
 * Created by iGeorge on 26/11/17.
 */

public interface ListActivityPresenter extends APIInteractor.OnItemLoadedListener{
    void initializePresenter();
}
