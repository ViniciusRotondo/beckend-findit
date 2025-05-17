package com.project.findit.services;

import com.project.findit.dtos.EventRecordDto;
import com.project.findit.models.EventModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface EventService {
    public EventModel createEvent(EventRecordDto eventDto);;
    public List<EventModel> getAllEvents();
    public Optional<EventModel> getEventsDetails(UUID id);
    public EventModel updateEventDatails(UUID id, EventModel eventModel);
    public void deleteEvent(UUID id);
}
