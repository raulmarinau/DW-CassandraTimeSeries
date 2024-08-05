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
import ro.uvt.info.dw.repository.AssetsRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/assets", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AssetsController {

    private final AssetsRepository assetsRepo;

    @GetMapping
    public Collection<String> getAssets() {
        return assetsRepo
                .findAll().stream()
                .map(Asset::id)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<?> getAssetById(@PathVariable String assetId) {
        Iterable<Asset> assets = assetsRepo.findAllById(assetId.replaceAll("-", "/"));
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}
