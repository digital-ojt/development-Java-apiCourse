package com.amoibeojt.api.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amoibeojt.api.dto.partsstock.PartsStockSearchDTO;
import com.amoibeojt.api.entity.PartsStock;

/**
 * 部品在庫テーブルリポジトリー
 *
 * @author	your name
 * 
 */
public interface PartsStockRepository extends JpaRepository<PartsStock, Integer>, JpaSpecificationExecutor<PartsStock> {
	
	//検索条件DTOからSpecificationを組み立て
    default List<PartsStock> searchByCriteria(PartsStockSearchDTO c) {
        Specification<PartsStock> spec = Specification.where((root, query, cb) ->
            cb.isFalse(root.get("deleteFlag"))
        );

        if (!CollectionUtils.isEmpty(c.getCenterId())) {
            spec = spec.and((root, q, cb) ->
                root.get("centerId").in(c.getCenterId()));
        }
        if (!CollectionUtils.isEmpty(c.getCategoryId())) {
            spec = spec.and((root, q, cb) ->
                root.get("categoryId").in(c.getCategoryId()));
        }
        if (!CollectionUtils.isEmpty(c.getStockId())) {
            spec = spec.and((root, q, cb) ->
                root.get("stockId").in(c.getStockId()));
        }
        if (StringUtils.hasText(c.getNamePattern())) {
            spec = spec.and((root, q, cb) ->
                cb.like(cb.lower(root.get("name")),
                        "%" + c.getNamePattern().toLowerCase() + "%"));
        }
        if (c.getAmountMin() != null) {
            spec = spec.and((root, q, cb) ->
                cb.ge(root.get("amount"), c.getAmountMin()));
        }
        if (c.getAmountMax() != null) {
            spec = spec.and((root, q, cb) ->
                cb.le(root.get("amount"), c.getAmountMax()));
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "stockId");
        
        return findAll(spec, sort);
    }

}
