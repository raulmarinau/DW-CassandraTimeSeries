package ro.uvt.info.dw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uvt.info.dw.mirror.Asset;
import ro.uvt.info.dw.mirror.TimeSeriesDefinition;
import ro.uvt.info.dw.repository.TimeSeriesDefinitionsRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/tsDefinitions", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TimeSeriesDefinitionsController {

    private final TimeSeriesDefinitionsRepository tsDefRepo;

    @GetMapping
    public Collection<String> getTSDefs() {
        return tsDefRepo
                .findAll().stream()
                .map(TimeSeriesDefinition::id)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{TSDefId}")
    public ResponseEntity<?> getTSDefsById(@PathVariable String TSDefId) {
        Iterable<TimeSeriesDefinition> tsDefs = tsDefRepo.findAllById(TSDefId.replaceAll("-", "/"));
        return new ResponseEntity<>(tsDefs, HttpStatus.OK);
    }
}
