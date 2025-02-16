package dev3.projet.sudoku.Observer;

import dev3.projet.sudoku.model.SudokuException;

import java.util.HashMap;
import java.util.Map;

public class SudokuEvent {

        private final ObservableEvent event;
        private final Map<String, Object> eventData;

        public SudokuEvent(ObservableEvent event) {
            if (event == null) {
                throw new SudokuException("Event cannot be null.");
            }
            this.event = event;
            this.eventData = new HashMap<>();
        }

        public SudokuEvent addData(String key, Object value) {
            if (key == null || key.isEmpty()) {
                throw new SudokuException("Key cannot be null or empty.");
            }
            eventData.put(key, value);
            return this;
        }

        public ObservableEvent getEvent() {
            return event;
        }

        public <T> T getEventData(String key, Class<T> type) {
            Object value = eventData.get(key);
            if (type.isInstance(value)) {
                return type.cast(value);
            }
            return null;
        }


        @Override
        public String toString() {
            return "OxonoEvent{" +
                    "event=" + event +
                    ", eventData=" + eventData +
                    '}';
        }
    }

