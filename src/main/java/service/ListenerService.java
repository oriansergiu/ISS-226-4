package service;


import repository.ListenerRepository;

public interface ListenerService {
    void setListenerRepository(ListenerRepository listenerRepository);
    void setPaperService(PaperService paperService);
}
