package com.coa.mapper;

import com.coa.model.address.*;
import com.coa.payload.request.address.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapper {
    private ModelMapper modelMapper;

//    public Barangay  mapToModel(BarangayRequest barangayRequest){
//        Barangay barangay = new Barangay();
//
//        if(barangayRequest == null) return null;
//
//        TypeMap<BarangayRequest, Barangay> typeMap = modelMapper.createTypeMap(BarangayRequest.class, Barangay.class)
//                .addMapping(BaseRequest::getCode,Barangay::setBrgyCode)
//                .addMapping(BaseRequest::getName,Barangay::setBrgyName);
//
//        typeMap.map(barangayRequest,barangay);
//
//        return barangay;
//    }
//
//    public Municipality mapToModel(MunicipalityRequest municipalityRequest){
//        Municipality municipality = new Municipality();
//
//        if(municipalityRequest == null)return  null;
//
//        TypeMap<MunicipalityRequest, Municipality> typeMap = modelMapper.createTypeMap(MunicipalityRequest.class, Municipality.class)
//                .addMapping(BaseRequest::getCode, Municipality :: setMunCode)
//                .addMapping(BaseRequest :: getName, Municipality :: setMunName);
//
//        typeMap.map(municipalityRequest, municipality);
//        return  municipality;
//    }
//
//    public Province mapToModel(ProvinceRequest provinceRequest){
//        Province province = new Province();
//
//        if(provinceRequest == null) return  null;
//
//        TypeMap<ProvinceRequest, Province> typeMap = modelMapper.createTypeMap(ProvinceRequest.class, Province.class)
//                .addMapping(BaseRequest::getCode, Province :: setProCode)
//                .addMapping(BaseRequest :: getName, Province :: setProName);
//
//        typeMap.map(provinceRequest,province);
//
//        return  province;
//
//    }
//
//    public Region mapToModel(RegionRequest regionRequest){
//        Region region = new Region();
//
//        TypeMap<RegionRequest, Region> typeMap = modelMapper.createTypeMap(RegionRequest.class, Region.class)
//                .addMapping(BaseRequest::getCode, Region :: setRegionCode)
//                .addMapping(BaseRequest :: getName, Region :: setRegionName);
//
//        typeMap.map(regionRequest,region);
//        return region;
//    }
    public Address mapToModel(AddressRequest addressRequest){

        Address address = new Address();
        address.setId(addressRequest.getId());
        address.setBarangay(addressRequest.getBarangay() == null ? null : this.mapToModel(new Barangay(),addressRequest.getBarangay()));
        address.setMunicipality(this.mapToModel(new Municipality(),addressRequest.getMunicipality()));
        address.setProvince(this.mapToModel(new Province(), addressRequest.getProvince()));
        address.setRegion(this.mapToModel(new Region(), addressRequest.getRegion()));


        return address;
    }


    public <S extends BaseRequest,T extends Base> T mapToModel(T t, S s){
        modelMapper = new ModelMapper();
        TypeMap<BaseRequest, Base> typeMap =  modelMapper.createTypeMap(BaseRequest.class, Base.class)
                .addMapping(BaseRequest :: getCode, Base::setCode )
                .addMapping(BaseRequest :: getName, Base ::setName);

        typeMap.map(s,t);

        return t;
    }

}
