package com.amoibeojt.api.service.partsstock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amoibeojt.api.dto.partsstock.PartsStockResponseDTO;
import com.amoibeojt.api.dto.partsstock.PartsStockSearchDTO;
import com.amoibeojt.api.entity.PartsStock;
import com.amoibeojt.api.repository.CenterInfoRepository;
import com.amoibeojt.api.repository.PartsCategoryInfoRepository;
import com.amoibeojt.api.repository.PartsStockRepository;

import lombok.RequiredArgsConstructor;

/**
 * 部品在庫照会のサービスの実装クラス（DTO にマッピング）
 * 
 * @author your name
 */

@Service
@RequiredArgsConstructor
public class PartsStockServiceImpl implements PartsStockService {

    private final PartsStockRepository repository;
    private final PartsCategoryInfoRepository categoryRepo;
    private final CenterInfoRepository centerRepo;

    @Override
    public List<PartsStockResponseDTO> search(PartsStockSearchDTO c) {
    	
        // エンティティからDTOへ変換
        List<PartsStock> entity = repository.searchByCriteria(c);
        
        return entity.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    private PartsStockResponseDTO toDto(PartsStock e) {
        // カテゴリ名を取得
        String categoryName = categoryRepo.findById(e.getCategoryId())
            .map(cat -> cat.getCategoryName())
            .orElse(null);

        // センター名を取得
        String centerName = centerRepo.findById(e.getCenterId())
            .map(c -> c.getCenterName())
            .orElse(null);

        return PartsStockResponseDTO.builder()
        	.stockId(     e.getStockId())
        	.categoryName(categoryName)
        	.centerName(  centerName)
            .name(        e.getName())
            .amount(      e.getAmount())
            .description( e.getDescription())
            .build();
    }
}
