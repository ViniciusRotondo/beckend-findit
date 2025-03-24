package com.project.findit.services;

import com.project.findit.models.EventModel;
import com.project.findit.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;


    @Override
    public EventModel createEvent(EventModel eventModel) {
        return eventRepository.save(eventModel);
    }

    @Override
    public List<EventModel> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<EventModel> getEventsDetails(UUID id) {
        return eventRepository.findById(id);
    }

    @Override
    public EventModel updateEventDatails(UUID id, EventModel eventModel) {
        EventModel existingEvent = eventRepository.findById(id).orElse(null);

        if(existingEvent != null){
            existingEvent.setData_hora(eventModel.getData_hora());
            existingEvent.setDescricao(eventModel.getDescricao());
            existingEvent.setDuracao(eventModel.getDuracao());
            existingEvent.setIndicativo_idade(eventModel.getIndicativo_idade());
            existingEvent.setNome_do_evento(eventModel.getNome_do_evento());
            existingEvent.setPreco(eventModel.getPreco());
            existingEvent.setTelefone(eventModel.getTelefone());
            existingEvent.setUrl_imagem(eventModel.getUrl_imagem());
            existingEvent.setStatus(eventModel.getStatus());
            existingEvent.setCategoria(eventModel.getCategoria());
            existingEvent.setLocal(eventModel.getLocal());
            existingEvent.setOrganizador(eventModel.getOrganizador());

            return eventRepository.save(existingEvent);
        } else {
            throw new RuntimeException("Evento não encontrado com esse ID: " + id);
        }
    }

    @Override
    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }
}
