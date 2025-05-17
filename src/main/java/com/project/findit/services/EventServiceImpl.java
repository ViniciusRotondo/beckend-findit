package com.project.findit.services;

import com.project.findit.dtos.EventRecordDto;
import com.project.findit.models.EventModel;
import com.project.findit.repositories.CategoryRepository;
import com.project.findit.repositories.EventRepository;
import com.project.findit.repositories.LocationRepository;
import com.project.findit.repositories.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public EventModel createEvent(EventRecordDto eventDto) {
        EventModel eventModel = new EventModel();
        eventModel.setNome_do_evento(eventDto.nome_do_evento());
        eventModel.setDescricao(eventDto.descricao());
        eventModel.setData_hora(eventDto.data_hora());
        eventModel.setUrl_imagem(eventDto.url_imagem());
        eventModel.setPreco(eventDto.preco());
        eventModel.setDuracao(eventDto.duracao());
        eventModel.setIndicativo_idade(eventDto.indicativo_idade());
        eventModel.setTelefone(eventDto.telefone());
        eventModel.setStatus(eventDto.status());

        // Relacionamentos:
        eventModel.setOrganizador(organizerRepository.findById(eventDto.organizador_id()).orElse(null));
        eventModel.setLocal(locationRepository.findById(eventDto.local_id()).orElse(null));
        eventModel.setCategoria(categoryRepository.findById(eventDto.categoria_id()).orElse(null));

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
